import fs from "node:fs";
import path from "node:path";
import { fileURLToPath } from "node:url";

const here = path.dirname(fileURLToPath(import.meta.url));
const sourceRoot = path.resolve(here, "../../../src/main/java");
const outputFile = path.join(here, "uml-members.js");

function javaFiles(directory) {
    return fs.readdirSync(directory, { withFileTypes: true }).flatMap((entry) => {
        const fullPath = path.join(directory, entry.name);
        return entry.isDirectory() ? javaFiles(fullPath) : entry.name.endsWith(".java") ? [fullPath] : [];
    });
}

function stripCommentsAndLiterals(source) {
    return source
        .replace(/\/\*[\s\S]*?\*\//g, " ")
        .replace(/\/\/[^\r\n]*/g, " ")
        .replace(/"(?:\\.|[^"\\])*"/g, "\"\"")
        .replace(/'(?:\\.|[^'\\])*'/g, "''");
}

function normalize(value) {
    return value
        .replace(/@[\w.]+(?:\s*\([^)]*\))?/g, " ")
        .replace(/\s+/g, " ")
        .trim();
}

function splitTopLevel(value, delimiter = ",") {
    const parts = [];
    let current = "";
    let angle = 0;
    let round = 0;
    let square = 0;
    for (const character of value) {
        if (character === "<") angle += 1;
        else if (character === ">") angle = Math.max(0, angle - 1);
        else if (character === "(") round += 1;
        else if (character === ")") round = Math.max(0, round - 1);
        else if (character === "[") square += 1;
        else if (character === "]") square = Math.max(0, square - 1);

        if (character === delimiter && angle === 0 && round === 0 && square === 0) {
            parts.push(current.trim());
            current = "";
        }
        else {
            current += character;
        }
    }
    if (current.trim()) parts.push(current.trim());
    return parts;
}

function visibilitySymbol(value, kind) {
    if (/\bpublic\b/.test(value) || kind === "interface") return "+";
    if (/\bprotected\b/.test(value)) return "#";
    if (/\bprivate\b/.test(value)) return "−";
    return "~";
}

function cleanType(value) {
    return normalize(value)
        .replace(/\s*<\s*/g, "<")
        .replace(/\s*>\s*/g, ">")
        .replace(/\s*,\s*/g, ", ")
        .replace(/\s*\[\s*\]/g, "[]")
        .replace(/\?\s+extends\s+/g, "? extends ")
        .replace(/\?\s+super\s+/g, "? super ");
}

function parseParameters(value) {
    if (!value.trim()) return [];
    return splitTopLevel(value).map((parameter, index) => {
        const cleaned = normalize(parameter)
            .replace(/\bfinal\s+/g, "")
            .replace(/\.\.\./g, "[]");
        const match = cleaned.match(/^(.*\S)\s+(\w+)$/);
        if (!match) return { name: `arg${index + 1}`, type: cleanType(cleaned) };
        return { name: match[2], type: cleanType(match[1]) };
    });
}

function parseMethod(header, typeName, kind) {
    const cleaned = normalize(header).replace(/\s+throws\s+.+$/, "");
    if (!cleaned.includes("(") || /^(if|for|while|switch|catch|try|synchronized)\b/.test(cleaned)) return null;

    const open = cleaned.indexOf("(");
    const close = cleaned.lastIndexOf(")");
    if (close < open) return null;

    const before = cleaned.slice(0, open).trim();
    const nameMatch = before.match(/(\w+)$/);
    if (!nameMatch) return null;
    const name = nameMatch[1];
    const prefix = before.slice(0, -name.length).trim();
    if (["new", "return", "throw"].includes(name)) return null;

    const modifiers = prefix.match(/^(?:(?:public|protected|private|static|final|abstract|default|synchronized|native|strictfp)\s+)*/)?.[0] || "";
    const returnType = cleanType(prefix.slice(modifiers.length));
    const constructor = name === typeName;
    if (!constructor && !returnType) return null;

    const parameters = parseParameters(cleaned.slice(open + 1, close));
    return {
        name,
        visibility: visibilitySymbol(cleaned, kind),
        static: /\bstatic\b/.test(modifiers),
        abstract: /\babstract\b/.test(modifiers) || kind === "interface",
        constructor,
        returnType: constructor ? "" : returnType,
        parameters
    };
}

function parseField(declaration, kind) {
    let cleaned = normalize(declaration);
    cleaned = cleaned.replace(/\s*=\s*[\s\S]*$/, "");
    if (!cleaned || cleaned.includes("(") || /\b(class|interface|enum|record)\b/.test(cleaned)) return [];
    const modifiers = cleaned.match(/^(?:(?:public|protected|private|static|final|transient|volatile)\s+)*/)?.[0] || "";
    const remainder = cleaned.slice(modifiers.length).trim();
    const pieces = splitTopLevel(remainder);
    const first = pieces.shift();
    if (!first) return [];
    const firstMatch = first.match(/^(.*\S)\s+(\w+)$/);
    if (!firstMatch) return [];
    const type = cleanType(firstMatch[1]);
    const names = [firstMatch[2], ...pieces.map((piece) => piece.replace(/\s*=.*$/, "").trim()).filter((piece) => /^\w+$/.test(piece))];
    return names.map((name) => ({
        name,
        type,
        visibility: visibilitySymbol(cleaned, kind),
        static: /\bstatic\b/.test(modifiers),
        readOnly: /\bfinal\b/.test(modifiers)
    }));
}

function parseType(file) {
    const original = fs.readFileSync(file, "utf8");
    const source = stripCommentsAndLiterals(original);
    const packageName = source.match(/\bpackage\s+([\w.]+)\s*;/)?.[1] || "";
    const declaration = /\b(?:(?:public|protected|private)\s+)?(?:(?:final|abstract|sealed|non-sealed)\s+)*(class|interface|enum|record)\s+(\w+)([^\{]*)\{/m.exec(source);
    if (!declaration) return null;

    const [, kind, name, rawHeader] = declaration;
    const header = normalize(rawHeader);
    const extendsMatch = header.match(/\bextends\s+(.+?)(?=\s+implements\s+|$)/);
    const implementsMatch = header.match(/\bimplements\s+(.+)$/);
    const extendsTypes = extendsMatch ? splitTopLevel(extendsMatch[1]).map(cleanType) : [];
    const implementsTypes = implementsMatch ? splitTopLevel(implementsMatch[1]).map(cleanType) : [];

    const openingBrace = declaration.index + declaration[0].lastIndexOf("{");
    const attributes = [];
    const operations = [];

    if (kind === "record") {
        const recordComponents = header.match(/^\s*\((.*)\)/)?.[1] || "";
        for (const component of parseParameters(recordComponents)) {
            attributes.push({
                name: component.name,
                type: component.type,
                visibility: "−",
                static: false,
                readOnly: true
            });
        }
    }
    let depth = 1;
    let buffer = "";

    for (let index = openingBrace + 1; index < source.length && depth > 0; index += 1) {
        const character = source[index];
        if (character === "{") {
            if (depth === 1) {
                const equalsIndex = buffer.indexOf("=");
                const parenthesisIndex = buffer.indexOf("(");
                const fieldWithInitializer = equalsIndex >= 0 && (parenthesisIndex < 0 || equalsIndex < parenthesisIndex);
                const method = fieldWithInitializer ? null : parseMethod(buffer, name, kind);
                if (method) operations.push(method);
                else if (fieldWithInitializer) attributes.push(...parseField(buffer, kind));
                buffer = "";
            }
            depth += 1;
        }
        else if (character === "}") {
            depth -= 1;
            if (depth === 1) buffer = "";
        }
        else if (depth === 1 && character === ";") {
            const equalsIndex = buffer.indexOf("=");
            const parenthesisIndex = buffer.indexOf("(");
            const fieldWithInitializer = equalsIndex >= 0 && (parenthesisIndex < 0 || equalsIndex < parenthesisIndex);
            const method = fieldWithInitializer ? null : parseMethod(buffer, name, kind);
            if (method) operations.push(method);
            else attributes.push(...parseField(buffer, kind));
            buffer = "";
        }
        else if (depth === 1) {
            buffer += character;
        }
    }

    const relativePath = path.relative(path.resolve(here, "../../.."), file).split(path.sep).join("/");
    return {
        name,
        kind,
        packageName,
        sourcePath: relativePath,
        extends: extendsTypes,
        implements: implementsTypes,
        attributes,
        operations
    };
}

const types = {};
const duplicateNames = new Map();
for (const file of javaFiles(sourceRoot)) {
    const type = parseType(file);
    if (!type) continue;
    if (types[type.name]) {
        duplicateNames.set(type.name, [...(duplicateNames.get(type.name) || [types[type.name].sourcePath]), type.sourcePath]);
        continue;
    }
    types[type.name] = type;
}

const sortedTypes = Object.fromEntries(Object.entries(types).sort(([left], [right]) => left.localeCompare(right)));
const banner = "// Generated from src/main/java by generate-uml-members.mjs. Do not edit by hand.\n";
fs.writeFileSync(outputFile, `${banner}window.UML_TYPES = ${JSON.stringify(sortedTypes, null, 2)};\n`, "utf8");

console.log(`Generated ${Object.keys(sortedTypes).length} UML types -> ${path.relative(process.cwd(), outputFile)}`);
if (duplicateNames.size) {
    console.warn(`Skipped ${duplicateNames.size} duplicate simple names: ${[...duplicateNames.keys()].join(", ")}`);
}
