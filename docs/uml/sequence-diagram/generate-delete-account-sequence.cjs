const fs = require("node:fs");
const path = require("node:path");
const sharp = require("sharp");

const width = 3560;
const height = 1660;
const topBoxY = 28;
const bottomBoxY = 1576;
const participantBoxWidth = 250;
const participantBoxHeight = 58;
const lifelineTop = topBoxY + participantBoxHeight;
const lifelineBottom = bottomBoxY;

const participants = [
    "AccountSettingsView",
    "DeleteAccountController",
    "DeleteAccountInputBoundary",
    "DeleteAccountInteractor",
    "DeleteAccountDataAccessInterface",
    "ProfileGateway",
    "ServerHttpClient",
    "SessionClearerInterface",
    "CurrentUserProvider",
    "DeleteAccountOutputBoundary",
    "DeleteAccountPresenter",
    "LogoutViewModel",
    "DeleteAccountViewModel"
].map((name, index) => ({ name, x: 160 + index * 270 }));

const escapeXml = (value) => String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");

function participantBox(participant, y) {
    const left = participant.x - participantBoxWidth / 2;
    return `
        <rect x="${left}" y="${y}" width="${participantBoxWidth}" height="${participantBoxHeight}"
              rx="3" fill="#f1edff" stroke="#9b7cff" stroke-width="1.5"/>
        <text x="${participant.x}" y="${y + 36}" text-anchor="middle"
              font-family="Arial, Helvetica, sans-serif" font-size="15" font-weight="600"
              fill="#202124">${escapeXml(participant.name)}</text>`;
}

function message(fromIndex, toIndex, y, label, options = {}) {
    const from = participants[fromIndex];
    const to = participants[toIndex];
    const dashed = options.dashed ? ' stroke-dasharray="8 6"' : "";
    const color = options.error ? "#b42318" : "#30343a";
    const marker = options.error ? "arrow-error" : "arrow";
    const labelColor = options.error ? "#b42318" : "#202124";
    return `
        <line x1="${from.x}" y1="${y}" x2="${to.x}" y2="${y}"
              stroke="${color}" stroke-width="1.7"${dashed} marker-end="url(#${marker})"/>
        <text x="${(from.x + to.x) / 2}" y="${y - 9}" text-anchor="middle"
              font-family="Arial, Helvetica, sans-serif" font-size="17" fill="${labelColor}">
            ${escapeXml(label)}
        </text>`;
}

function note(x, y, noteWidth, noteHeight, text) {
    return `
        <path d="M ${x} ${y} H ${x + noteWidth - 18} L ${x + noteWidth} ${y + 18}
                 V ${y + noteHeight} H ${x} Z"
              fill="#fff7bf" stroke="#c9a900" stroke-width="1.3"/>
        <path d="M ${x + noteWidth - 18} ${y} V ${y + 18} H ${x + noteWidth}"
              fill="none" stroke="#c9a900" stroke-width="1.3"/>
        <text x="${x + 14}" y="${y + 25}" font-family="Arial, Helvetica, sans-serif"
              font-size="16" fill="#202124">${escapeXml(text)}</text>`;
}

function activation(index, y, activationHeight) {
    const participant = participants[index];
    return `<rect x="${participant.x - 7}" y="${y}" width="14" height="${activationHeight}"
                  fill="#f6f6f6" stroke="#7b7f86" stroke-width="1"/>`;
}

const lifelines = participants.map((participant) => `
    <line x1="${participant.x}" y1="${lifelineTop}" x2="${participant.x}" y2="${lifelineBottom}"
          stroke="#9b7cff" stroke-width="1.4"/>`).join("");

const boxes = participants.map((participant) => participantBox(participant, topBoxY)).join("")
    + participants.map((participant) => participantBox(participant, bottomBoxY)).join("");

const activations = [
    activation(1, 196, 150),
    activation(2, 258, 88),
    activation(3, 320, 1220),
    activation(4, 406, 360),
    activation(5, 466, 220),
    activation(6, 526, 100),
    activation(7, 776, 220),
    activation(8, 836, 100),
    activation(9, 1026, 450),
    activation(10, 1086, 454),
    activation(11, 1146, 100),
    activation(12, 1496, 48)
].join("");

const sequence = [
    note(34, 112, 470, 54, "entry point: user confirms permanent account deletion"),
    note(1090, 112, 720, 54, "online path shown; LocalProfileRepository implements the same port offline"),
    message(0, 1, 210, "deleteAccount()"),
    message(1, 2, 272, "execute()"),
    message(2, 3, 334, "execute()"),

    `<rect x="900" y="374" width="2620" height="1168" fill="none" stroke="#565b63" stroke-width="1.5"/>
     <path d="M 900 374 H 1000 L 1020 396 H 900 Z" fill="#f5f6f8" stroke="#565b63" stroke-width="1.2"/>
     <text x="918" y="391" font-family="Arial, Helvetica, sans-serif" font-size="16" font-weight="700" fill="#202124">alt</text>
     <text x="1034" y="394" font-family="Arial, Helvetica, sans-serif" font-size="16" fill="#202124">account deletion succeeds</text>`,

    message(3, 4, 430, "deleteAccount()"),
    message(4, 5, 490, "deleteAccount()"),
    message(5, 6, 550, 'delete("/api/profile")'),
    message(6, 5, 610, "204 No Content", { dashed: true }),
    message(5, 4, 670, "void", { dashed: true }),
    message(4, 3, 730, "void", { dashed: true }),
    message(3, 7, 800, "clearSession()"),
    message(7, 8, 860, "clearSession()"),
    message(8, 7, 920, "void", { dashed: true }),
    message(7, 3, 980, "void", { dashed: true }),
    message(3, 9, 1050, "prepareSuccessView()"),
    message(9, 10, 1110, "prepareSuccessView()"),
    message(10, 11, 1170, "setLoggedOut()"),
    message(11, 10, 1230, "void", { dashed: true }),

    `<line x1="900" y1="1280" x2="3520" y2="1280" stroke="#565b63" stroke-width="1.3" stroke-dasharray="9 6"/>
     <rect x="900" y="1280" width="310" height="31" fill="#f5f6f8" stroke="#565b63" stroke-width="1.1"/>
     <text x="916" y="1301" font-family="Arial, Helvetica, sans-serif" font-size="16" fill="#202124">account deletion fails</text>`,

    message(4, 3, 1340, "throws DataAccessException", { dashed: true, error: true }),
    message(3, 9, 1400, "prepareFailView(errorMessage)", { error: true }),
    message(9, 10, 1460, "prepareFailView(errorMessage)", { error: true }),
    message(10, 12, 1520, "setErrorMessage(errorMessage)", { error: true })
].join("");

const svg = `<?xml version="1.0" encoding="UTF-8"?>
<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}" viewBox="0 0 ${width} ${height}">
    <defs>
        <marker id="arrow" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto">
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#30343a"/>
        </marker>
        <marker id="arrow-error" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto">
            <path d="M 0 0 L 10 5 L 0 10 z" fill="#b42318"/>
        </marker>
    </defs>
    <rect width="100%" height="100%" fill="#ffffff"/>
    ${lifelines}
    ${sequence}
    ${activations}
    ${boxes}
</svg>`;

const outputPath = path.join(__dirname, "DeleteAccountSequence-AlanXue.png");
sharp(Buffer.from(svg))
    .png({ compressionLevel: 9 })
    .toFile(outputPath)
    .then(() => console.log(`Generated ${outputPath}`))
    .catch((error) => {
        console.error(error);
        process.exitCode = 1;
    });
