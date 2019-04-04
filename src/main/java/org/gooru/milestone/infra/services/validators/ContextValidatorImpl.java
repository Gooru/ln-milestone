package org.gooru.milestone.infra.services.validators;

import org.gooru.milestone.infra.data.ProcessingContext;
import org.gooru.milestone.infra.jdbi.DbiRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ashish.
 */

class ContextValidatorImpl implements ContextValidator {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContextValidatorImpl.class);
  private final DbiRegistry dbiRegistry;
  private ProcessingContext context;
  private ContextValidatorDao contextValidatorDSDao;
  private ContextValidatorDao contextValidatorCoreDao;

  ContextValidatorImpl(DbiRegistry dbiRegistry) {
    this.dbiRegistry = dbiRegistry;
  }

  @Override
  public void validate(ProcessingContext context) {
    this.context = context;
    validateCourse();
    validateGradeExistenceForFWAndSubject();
  }

  private void validateCourse() {
    if (!fetchCoreDao().validateCourseAndItsPremiumness(context.getCourseId())) {
      LOGGER
          .warn("Invalid/non existing/non premium course  '{}'", context.getCourseId().toString());
      throw new IllegalArgumentException(
          "Invalid/Deleted/Non premium course: '" + context.getCourseId().toString());
    }
  }

  private void validateGradeExistenceForFWAndSubject() {
    if (!fetchDSDao().checkGradeExistsForSubjectFW(context.getSubject(), context.getFwCode())) {
      LOGGER.warn("Grades do not exists for subject: '{}' and FW: '{}'", context.getSubject(),
          context.getFwCode());
      throw new IllegalArgumentException(
          "Invalid Subject: '" + context.getSubject() + "' FW: '" + context.getFwCode()
              + "' combination");
    }
  }

  private ContextValidatorDao fetchDSDao() {
    if (contextValidatorDSDao == null) {
      contextValidatorDSDao = dbiRegistry.getDsdbDbi().onDemand(ContextValidatorDao.class);
    }
    return contextValidatorDSDao;
  }

  private ContextValidatorDao fetchCoreDao() {
    if (contextValidatorCoreDao == null) {
      contextValidatorCoreDao = dbiRegistry.getNucleusDbi().onDemand(ContextValidatorDao.class);
    }
    return contextValidatorCoreDao;
  }
}
