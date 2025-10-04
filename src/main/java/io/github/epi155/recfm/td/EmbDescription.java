package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.EmbModel;
import io.github.epi155.recfm.api.TraitModel;
import io.github.epi155.recfm.proxy.EmbProxy;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

@Slf4j
public class EmbDescription extends TypeDescription {
    private final CodeFactory factory;

    public EmbDescription(CodeFactory factory) {
        super(EmbModel.class, "!Emb");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("src", TraitModel.class, null, "setSource");
        substituteProperty("ref", String.class, null, "setReference");
    }
    public Object newInstance(Node node) {
        return new EmbProxy(factory);
    }

    /* nella 2.2 si perde il return, ma è stato FIXato nella 2.5 */
    @Override
    public Object finalizeConstruction(Object obj) {
        if (obj instanceof EmbProxy) {
            EmbProxy proxy = (EmbProxy) obj;
            return  proxy.getDelegate();
        }
        return obj;
    }

}
