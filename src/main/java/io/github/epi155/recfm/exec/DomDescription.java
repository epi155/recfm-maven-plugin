package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.DomModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class DomDescription extends TypeDescription {
    private final CodeFactory factory;

    public DomDescription(CodeFactory factory) {
        super(DomModel.class, "!Dom");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("red", boolean.class, null, "setRedefines");
    }
    public Object newInstance(Node node) {
        return factory.newDomModel();
    }
}
