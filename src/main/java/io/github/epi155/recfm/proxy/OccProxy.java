package io.github.epi155.recfm.proxy;

import io.github.epi155.recfm.api.CodeFactory;
import io.github.epi155.recfm.api.OccTraitModel;
import io.github.epi155.recfm.api.TraitModel;

import static io.github.epi155.recfm.exec.RecordFormatMojo.pluginContext;

public class OccProxy implements OccTraitModel, ProxyField<OccTraitModel> {

    private final OccTraitModel delegate;
    private int kMode = 0;

    public OccProxy(CodeFactory factory) {
        this.delegate = factory.newOccTraitModel();
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
    public void setName(String s) {
        delegate.setName(s);
    }

    @Override
    public void setOverride(boolean b) {
        delegate.setOverride(b);
    }

    @Override
    public void setTypedef(TraitModel traitModel) {
        if (kMode<0) throw new IllegalArgumentException();
        delegate.setTypedef(traitModel);
        kMode++;
    }

    @Override
    public void setTimes(int i) {
        delegate.setTimes(i);
    }

    @Override
    public OccTraitModel getDelegate() {
        return delegate;
    }

    public void setReference(String traitName) {
        if (traitName == null) return;
        if (kMode>0) throw new IllegalArgumentException();
        TraitModel trait = pluginContext.get().getTrait(traitName);
        if (trait == null) {
            throw new IllegalArgumentException("Missing interface " + traitName);
        }
        delegate.setTypedef(trait);
        kMode--;
    }

}
