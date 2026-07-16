package com.scholarmatch.usecase.logout;

/**
 * Output data produced by a successful logout.
 *
 * <p>Empty for now — the only thing the view needs to know is that logout happened,
 * which the presenter signals simply by invoking
 * LogoutOutputBoundary#prepareSuccessView — but is kept as its own type for
 * symmetry with the other use cases' output data.
 */
public final class LogoutOutputData {
}
