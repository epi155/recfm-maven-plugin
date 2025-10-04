

import static io.github.epi155.recfm.exec.RecordFormatMojo.pluginContext;
import io.github.epi155.recfm.api.TraitModel;
import io.github.epi155.recfm.proxy.ProxyField;

import org.yaml.snakeyaml.constructor.BaseConstructor;

public aspect EmbAspect {
    // add setReference to EmbModel (proxied by EmbProxy)
    public abstract void io.github.epi155.recfm.api.EmbModel.setReference(String traitName);

    // add setReference to GrpTraitModel (proxied by GrpProxy)
    public abstract void io.github.epi155.recfm.api.GrpTraitModel.setReference(String traitName);

    // add setReference to OccTraitModel (proxied by OccProxy)
    public abstract void io.github.epi155.recfm.api.OccTraitModel.setReference(String traitName);


    // I replace the proxy object with the original one
//    pointcut ctorCall(): execution( protected Object BaseConstructor.constructObjectNoCheck(..) ) ;
//    Object around(): ctorCall() {
//        Object result = proceed();
//        if (result instanceof ProxyField) {
//            ProxyField proxy = (ProxyField) result;
//            result = proxy.getDelegate();
//        }
//        return result;
//    }
}