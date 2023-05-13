package io.github.epi155.recfm.exec;

import org.jetbrains.annotations.NotNull;

public class CodeDriverException extends RuntimeException {
    public CodeDriverException() {
        super("Code Provider not Found");
    }

    public CodeDriverException(@NotNull String codeProviderClassName) {
        super("Code Provider <"+codeProviderClassName+"> not Found");
    }
}
