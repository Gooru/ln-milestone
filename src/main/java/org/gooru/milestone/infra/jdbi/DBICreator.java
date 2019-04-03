package org.gooru.milestone.infra.jdbi;

import javax.sql.DataSource;
import org.gooru.milestone.infra.components.DataSourceRegistry;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */
public final class DBICreator {

  public static DBI getDbiForDefaultDS() {
    return createDBI(DataSourceRegistry.getInstance().getDefaultDataSource());
  }

  public static DBI getDbiForDsdbDS() {
    return createDBI(DataSourceRegistry.getInstance().getDsdbDataSource());
  }

  public static DBI getDbiForAnalyticsDS() {
    return createDBI(DataSourceRegistry.getInstance().getAnalyticsDataSource());
  }

  private static DBI createDBI(DataSource dataSource) {
    DBI dbi = new DBI(dataSource);
    dbi.registerArgumentFactory(new PostgresIntegerArrayArgumentFactory());
    dbi.registerArgumentFactory(new PostgresStringArrayArgumentFactory());
    dbi.registerArgumentFactory(new PostgresUUIDArrayArgumentFactory());
    return dbi;
  }

  private DBICreator() {
    throw new AssertionError();
  }
}
