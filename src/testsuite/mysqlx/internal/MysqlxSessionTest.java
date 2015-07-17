/*
  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FLOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package testsuite.mysqlx.internal;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.mysqlx.MysqlxError;
import com.mysql.cj.mysqlx.MysqlxSession;

/**
 * Tests for (internal) session-level APIs against a running MySQL-X server.
 */
public class MysqlxSessionTest extends BaseInternalMysqlxTest {
    private MysqlxSession session;

    @Before
    public void setupTestSession() {
        this.session = createTestSession();
    }

    @After
    public void destroyTestSession() {
        this.session.close();
    }

    @Test
    public void testCreateDropCollection() {
        String collName = "toBeCreatedAndDropped";
        try {
            this.session.dropCollection(getTestDatabase(), collName);
        } catch (MysqlxError ex) {
            System.err.println(ex.getMessage());
        }
        assertFalse(this.session.tableExists(getTestDatabase(), collName));
        this.session.createCollection(getTestDatabase(), collName);
        assertTrue(this.session.tableExists(getTestDatabase(), collName));
        this.session.dropCollection(getTestDatabase(), collName);
        assertFalse(this.session.tableExists(getTestDatabase(), collName));
        this.session.createCollection(getTestDatabase(), collName);
        assertTrue(this.session.tableExists(getTestDatabase(), collName));
        this.session.dropCollection(getTestDatabase(), collName);
        assertFalse(this.session.tableExists(getTestDatabase(), collName));
        this.session.createCollection(getTestDatabase(), collName);
        assertTrue(this.session.tableExists(getTestDatabase(), collName));
        this.session.dropCollection(getTestDatabase(), collName);
        assertFalse(this.session.tableExists(getTestDatabase(), collName));
    }

    @Test
    public void testGetObjects() {
        String collName = "testGetObjects";
        try {
            this.session.dropCollection(getTestDatabase(), collName);
        } catch (MysqlxError ex) {
            System.err.println(ex.getMessage());
        }
        this.session.createCollection(getTestDatabase(), collName);
        List<String> collNames = this.session.getObjectNamesOfType(getTestDatabase(), "COLLECTION");
        assertTrue(collNames.contains(collName));
        this.session.dropCollection(getTestDatabase(), collName);
    }
}
