package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.GrpTraitModel;
import io.github.epi155.recfm.api.TraitModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class GrpTraitDescription extends TypeDescription {
    private final CodeFactory factory;

    public GrpTraitDescription(CodeFactory factory) {
        super(GrpTraitModel.class, "!GRP");
        this.factory = factory;
        substituteProperty("at", int.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("red", boolean.class, null, "setRedefines");
        substituteProperty("as", TraitModel.class, null, "setTypedef");
    }
    public Object newInstance(Node node) {
        return factory.newGrpTraitModel();
    }
}
