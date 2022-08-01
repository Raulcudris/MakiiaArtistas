package com.makiia.modules.bus.services;

import com.makiia.crosscutting.domain.enums.ProcessName;
import com.makiia.crosscutting.domain.enums.Status;
import com.makiia.crosscutting.domain.model.traceability.TransactionFile;
import com.makiia.crosscutting.exceptions.ExceptionBuilder;
import com.makiia.crosscutting.exceptions.Main.EBusinessException;
import com.makiia.crosscutting.exceptions.MicroEventException;
import com.makiia.modules.bus.contracts.IjpaDataProviders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class UsecaseServices <T, K> {

    protected K ijpaDataProvider;

    @Autowired
    private TraceabilityService traceabilityService;

    /**
     * genera el map que se insertará en la trazabilidad
     *
     * @return Map
     */
    public Map<String, String> getAllTraceability() {
        return new HashMap<>();
    }

    /**
     * obtiene una lista de entidades dto
     *
     * @return List<T>
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public List<T> getAll() throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), getAllTraceability());

            return ((IjpaDataProviders<T>) ijpaDataProvider).getAll();
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), getAllTraceability());

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * genera el map que se insertará en la trazabilidad
     *
     * @return Map
     */
    public Map<String, String> getTraceability() {
        return new HashMap<>();
    }

    /**
     * obtiene una un entidad de dto
     *
     * @param id string
     * @return List<T>
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public T get(int id) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), getTraceability());

            return ((IjpaDataProviders<T>) ijpaDataProvider).get(id);
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), getTraceability());

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * genera el map que se insertará en la trazabilidad
     *
     * @return Map
     */
    public Map<String, String> createTraceability() {
        return new HashMap<>();
    }

    /**
     * realiza una operacion antes de enviar el create de la entidad dto
     *
     * @param dto T
     * @return T
     */
    public T afterCreate(T dto) throws EBusinessException {
        try {
            return dto;
        } catch (Exception e) {
            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * realiza una operacion antes de enviar el create de la entidad dto
     *
     * @param dto T
     * @return T
     */
    public T beforeCreate(T dto) throws EBusinessException {
        try {
            return dto;
        } catch (Exception e) {
            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * crea una entidad de dto y la envia a la persistencia
     *
     * @param dto T
     * @return List<T>
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public T create(T dto) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), createTraceability());

            return afterCreate(((IjpaDataProviders<T>) ijpaDataProvider).save(beforeCreate(dto)));
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), createTraceability());

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * crea una lista de entidades dtos
     *
     * @param dtos List<T>
     * @return List<T>
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public List<T> create(List<T> dtos) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();
        List<T> dtosNew = new ArrayList<>();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), createTraceability());

            for (T dto : dtos) {
                dtosNew.add(beforeCreate(dto));
            }

            dtos = ((IjpaDataProviders<T>) ijpaDataProvider).save(dtosNew);
            dtosNew = new ArrayList<>();

            for (T dto : dtos) {
                dtosNew.add(afterCreate(dto));
            }

            return dtosNew;
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), createTraceability());

            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * genera el map que se insertará en la trazabilidad
     *
     * @return Map
     */
    public Map<String, String> updateTraceability() {
        return new HashMap<>();
    }

    /**
     * realiza una operacion antes de enviar el update de la entidad dto
     *
     * @param dto T
     * @return T
     */
    public T beforeUpdate(T dto) throws EBusinessException {
        try {
            return dto;
        } catch (Exception e) {
            throw ExceptionBuilder.builder()
                    .withMessage(e.getMessage())
                    .withParentException(e)
                    .buildBusinessException();
        }
    }

    /**
     * actualiza una entidad de dto
     *
     * @param id  int
     * @param dto T
     * @return List<T>
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public T update(int id, T dto) throws EBusinessException, MicroEventException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), updateTraceability());

            return ((IjpaDataProviders<T>) ijpaDataProvider).update(id, beforeUpdate(dto));
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), updateTraceability());

            throw ExceptionBuilder.builder()
                    .withParentException(e)
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .buildBusinessException();
        }
    }

    /**
     * genera el map que se insertará en la trazabilidad
     *
     * @return Map
     */
    public Map<String, String> deleteTraceability() {
        return new HashMap<>();
    }

    /**
     * elimina una entidad de dto
     *
     * @param id int
     * @throws EBusinessException  excepcion
     * @throws MicroEventException excepcion
     */
    public void delete(int id) throws MicroEventException, EBusinessException {
        String transactionalId = UUID.randomUUID().toString();

        try {
            traceabilityService.createTraceabilityEventDocument(transactionalId,
                    Status.INITIAL.getDescription(), TransactionFile.builder().build(),
                    ProcessName.EXTERNAL.getDescription(), ProcessName.RECEPTION.getName(),
                    ProcessName.RECEPTION.getDescription(), deleteTraceability());

            ((IjpaDataProviders<T>) ijpaDataProvider).delete(id);
        } catch (EBusinessException e) {
            traceabilityService.createTraceabilityEventDocument(transactionalId, Status.FAIL.getDescription(),
                    TransactionFile.builder().build(), ProcessName.EXTERNAL.getDescription(),
                    ProcessName.RECEPTION.getName(), ProcessName.RECEPTION.getDescription(), deleteTraceability());

            throw ExceptionBuilder.builder()
                    .withParentException(e)
                    .withMessage(e.getMessage())
                    .withCode(e.getCode())
                    .buildBusinessException();
        }
    }

}
