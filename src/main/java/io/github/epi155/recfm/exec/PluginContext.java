package io.github.epi155.recfm.exec;

import io.github.epi155.recfm.api.TraitModel;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class PluginContext {
    @Setter
    private String currentPackage;
    private final Map<String, TraitModel> traitStore = new HashMap<>();

    public boolean addTrait(TraitModel trait) {
        return traitStore.put(fullName(trait.getName()), trait) != null;
    }

    public TraitModel getTrait(String name) {
        return traitStore.get(fullName(name));
    }

    private String fullName(String name) {
        if (currentPackage == null) return name;
        return currentPackage + "." + name;
    }
}
