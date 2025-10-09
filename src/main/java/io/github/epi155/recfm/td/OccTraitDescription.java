package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.OccTraitModel;
import io.github.epi155.recfm.api.TraitModel;
import io.github.epi155.recfm.proxy.OccProxy;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class OccTraitDescription extends TypeDescription {
    private final CodeFactory factory;

    public OccTraitDescription(CodeFactory factory) {
        super(OccTraitModel.class, "!OCC");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("ovr", boolean.class, null, "setOverride");
        substituteProperty("as", TraitModel.class, null, "setTypedef");
        substituteProperty("x", int.class, null, "setTimes");
        substituteProperty("ref", String.class, null, "setReference");
    }
    public Object newInstance(Node node) {
        return new OccProxy(factory);
    }

    /* nella 2.2 si perde il return, ma è stato FIXato nella 2.5 */
    @Override
    public Object finalizeConstruction(Object obj) {
        if (obj instanceof OccProxy) {
            OccProxy proxy = (OccProxy) obj;
            return  proxy.getDelegate();
        }
        return obj;
    }

}
