package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.EmbModel;
import io.github.epi155.recfm.api.TraitModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class EmbDescription extends TypeDescription {
    private final CodeFactory factory;

    public EmbDescription(CodeFactory factory) {
        super(EmbModel.class, "!Emb");
        this.factory = factory;
        substituteProperty("at", Integer.class, null, "setOffset");
        substituteProperty("len", int.class, null, "setLength");
        substituteProperty("src", TraitModel.class, null, "setSource");
    }
    public Object newInstance(Node node) {
        return factory.newEmbModel();
    }
}
