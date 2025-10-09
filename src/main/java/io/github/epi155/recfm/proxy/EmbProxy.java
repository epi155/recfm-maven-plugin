package io.github.epi155.recfm.proxy;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.EmbModel;
import io.github.epi155.recfm.api.TraitModel;
import lombok.Getter;

import static io.github.epi155.recfm.exec.RecordFormatMojo.pluginContext;

public class EmbProxy implements EmbModel, ProxyField<EmbModel> {

    @Getter
    private final EmbModel delegate;
    private int kMode = 0;

    public EmbProxy(CodeFactory factory) {
        this.delegate = factory.newEmbModel();
    }
    @Override
    public void setOffset(Integer integer) {
        delegate.setOffset(integer);
    }

    @Override
    public Integer getOffset() {
        return delegate.getOffset();
    }

    @Override
    public void setLength(int i) {
        delegate.setLength(i);
    }

    @Override
    public int getLength() {
        return delegate.getLength();
    }

    @Override
    public void setSource(TraitModel trait) {
        if (kMode<0) throw new IllegalArgumentException();
        delegate.setSource(trait);
        kMode++;
    }
    public void setReference(String traitName) {
        if (traitName == null) return;
        if (kMode>0) throw new IllegalArgumentException();
        TraitModel trait = pluginContext.get().getTrait(traitName);
        if (trait == null) {
            throw new IllegalArgumentException("Missing interface " + traitName);
        }
        delegate.setSource(trait);
        kMode--;
    }
}
