package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CheckChar;
import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.FilModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class FilDescription extends TypeDescription {
    private final CodeFactory factory;

    public FilDescription(CodeFactory factory) {
        super(FilModel.class, "!Fil");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("fill", Character.class, null, "setFillChar");
        substituteProperty("chk", CheckChar.class, null, "setCheck");
    }
    public Object newInstance(Node node) {
        return factory.newFilModel();
    }
}