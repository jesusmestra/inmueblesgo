package com.j2km.inmueblesgo.service;

import com.j2km.inmueblesgo.domain.TipoPlantaDetalleEntity;
import com.j2km.inmueblesgo.domain.TipoPlantaEntity;
import java.util.List;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.deltaspike.data.api.criteria.CriteriaSupport;

@Repository
public abstract class TipoPlantaDetalleRepository implements EntityRepository<TipoPlantaDetalleEntity, Long> , CriteriaSupport<TipoPlantaDetalleEntity> {
    public abstract List<TipoPlantaDetalleEntity> findByTipoPlanta(
            TipoPlantaEntity tipoPlanta
    );
    
    public abstract TipoPlantaDetalleEntity findOptionalByTipoPlantaAndNumero(
            TipoPlantaEntity tipoPlanta, Integer numero
    );
}