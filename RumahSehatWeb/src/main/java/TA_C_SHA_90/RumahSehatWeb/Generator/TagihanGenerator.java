package TA_C_SHA_90.RumahSehatWeb.Generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TagihanGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object object)
            throws HibernateException {
        String prefix = "BILL-";
        String suffix = "";
        try {
            Connection connection = session.connection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select count(kode) from tagihan");
            if (resultSet.next()) {
                Integer id = resultSet.getInt(1) + 1;
                suffix = id.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + suffix;
    }
}
