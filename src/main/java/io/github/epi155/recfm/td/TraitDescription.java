package io.github.epi155.recfm.td;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.TraitModel;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.nodes.Node;

import static io.github.epi155.recfm.exec.RecordFormatMojo.pluginContext;

@Slf4j
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
    @Override
    public Object finalizeConstruction(Object obj) {
        // qui l’oggetto è già completo
        if (obj instanceof TraitModel) {
            TraitModel trait = (TraitModel) obj;
            if (pluginContext.get().addTrait(trait)) {
                log.warn("Interface {} overwritten", trait.getName());
            }
        }
        return obj;
    }
}
