package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.GrpModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class GrpDescription extends TypeDescription {
    private final CodeFactory factory;

    public GrpDescription(CodeFactory factory) {
        super(GrpModel.class, "!Grp");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("ovr", boolean.class, null, "setOverride");
    }
    public Object newInstance(Node node) {
        return factory.newGrpModel();
    }
}
