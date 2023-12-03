import jdbc.DbManagerTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DbManagerTestSuite {

    @Test
    void testSelectUsersAndPosts() throws SQLException {
        //given
        DbManagerTask dbManager = DbManagerTask.getInstance();
        //when
        String sqlQuery = """
                SELECT U.FIRSTNAME, U.LASTNAME, I.SUMMARY, I.DESCRIPTION
                FROM USERS U
                JOIN ISSUES I ON U.ID = I.USER_ID_ASSIGNEDTO
                """;
        Statement statement = dbManager.getConnection().createStatement();
        ResultSet rs = statement.executeQuery(sqlQuery);
        //then
        while(rs.next()){
            System.out.println(rs.getString("FIRSTNAME") + ", " +
                    rs.getString("LASTNAME") + ", " +
                    rs.getString("SUMMARY") + ", " +
                    rs.getString("DESCRIPTION"));
        }
        rs.close();
        System.out.println();
        String sqlQuerySelect2 = """
                SELECT U.FIRSTNAME, U.LASTNAME, COUNT(*) AS POSTS_NUMBER
                FROM USERS U
                JOIN ISSUES I ON U.ID = I.USER_ID_ASSIGNEDTO
                GROUP BY U.ID
                HAVING COUNT(*) > 2;
                """;
        ResultSet rsSelect2 = statement.executeQuery(sqlQuerySelect2);
        int counter = 0;
        while(rsSelect2.next()){
            System.out.println(rsSelect2.getString("FIRSTNAME") + ", " +
                    rsSelect2.getString("LASTNAME"));
            counter++;
        }
        rsSelect2.close();
        statement.close();
        assertEquals(1, counter);
    }
}
