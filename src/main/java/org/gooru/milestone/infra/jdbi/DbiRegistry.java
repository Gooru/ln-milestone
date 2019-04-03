package org.gooru.milestone.infra.jdbi;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public class DbiRegistry {

  private final DBI nucleusDbi;
  private final DBI analyitcsDbi;
  private final DBI dsdbDbi;


  private DbiRegistry(DBI nucleusDbi, DBI analyitcsDbi, DBI dsdbDbi) {
    this.nucleusDbi = nucleusDbi;
    this.analyitcsDbi = analyitcsDbi;
    this.dsdbDbi = dsdbDbi;
  }

  public DBI getNucleusDbi() {
    return nucleusDbi;
  }

  public DBI getAnalyitcsDbi() {
    return analyitcsDbi;
  }

  public DBI getDsdbDbi() {
    return dsdbDbi;
  }

  public static DbiRegistry build() {
    return new DbiRegistry(DBICreator.getDbiForDefaultDS(), DBICreator.getDbiForAnalyticsDS(),
        DBICreator.getDbiForDsdbDS());
  }

}
