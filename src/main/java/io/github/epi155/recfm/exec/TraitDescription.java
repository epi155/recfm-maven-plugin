package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.TraitModel;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

public class TraitDescription extends TypeDescription {
    private final CodeFactory factory;

    public TraitDescription(CodeFactory factory) {
        super(TraitModel.class);
        this.factory = factory;
        substituteProperty("len", int.class, null, "setLength");
    }
    public Object newInstance(Node node) {
        return factory.newTraitModel();
    }
}
