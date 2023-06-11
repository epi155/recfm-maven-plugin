package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.ValModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class ValDescription extends TypeDescription {
    private final CodeFactory factory;

    public ValDescription(CodeFactory factory) {
        super(ValModel.class, "!Val");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("val", String.class, null, "setValue");
    }
    public Object newInstance(Node node) {
        return factory.newValModel();
    }
}
