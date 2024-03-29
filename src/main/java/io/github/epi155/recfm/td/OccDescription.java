package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.OccModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class OccDescription extends TypeDescription {
    private final CodeFactory factory;

    public OccDescription(CodeFactory factory) {
        super(OccModel.class, "!Occ");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("ovr", boolean.class, null, "setOverride");
        substituteProperty("x", int.class, null, "setTimes");
    }
    public Object newInstance(Node node) {
        return factory.newOccModel();
    }
}
