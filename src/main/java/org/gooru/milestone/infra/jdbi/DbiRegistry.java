package org.gooru.milestone.infra.jdbi;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public class DbiRegistry {

  private final DBI nucleusDbi;
  private final DBI analyticsDbi;
  private final DBI dsdbDbi;


  private DbiRegistry(DBI nucleusDbi, DBI analyticsDbi, DBI dsdbDbi) {
    this.nucleusDbi = nucleusDbi;
    this.analyticsDbi = analyticsDbi;
    this.dsdbDbi = dsdbDbi;
  }

  public DBI getNucleusDbi() {
    return nucleusDbi;
  }

  public DBI getAnalyticsDbi() {
    return analyticsDbi;
  }

  public DBI getDsdbDbi() {
    return dsdbDbi;
  }

  public static DbiRegistry build() {
    return new DbiRegistry(DBICreator.getDbiForDefaultDS(), DBICreator.getDbiForAnalyticsDS(),
        DBICreator.getDbiForDsdbDS());
  }

  public static DbiRegistry build(DBI nucleusDbi, DBI analyticsDbi, DBI dsdbDbi) {
    return new DbiRegistry(nucleusDbi, analyticsDbi, dsdbDbi);
  }

}
