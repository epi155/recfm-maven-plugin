package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.OccTraitModel;
import io.github.epi155.recfm.api.TraitModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class OccTraitDescription extends TypeDescription {
    private final CodeFactory factory;

    public OccTraitDescription(CodeFactory factory) {
        super(OccTraitModel.class, "!OCC");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("red", boolean.class, null, "setRedefines");
        substituteProperty("as", TraitModel.class, null, "setTypedef");
        substituteProperty("x", int.class, null, "setTimes");
    }
    public Object newInstance(Node node) {
        return factory.newOccTraitModel();
    }
}
