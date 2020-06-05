package TestBased;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class DBInfoRetriever {

    DBConnectionHandler conn_;

    public DBInfoRetriever(DBConnectionHandler conn){
        conn_ = conn;
    }

    public HashMap<String, String> getCriInfoByUserId(String userId){
        try {
            HashMap<String, String> resultMap = new HashMap<>();
            conn_.setDBName("u_card_cri_fis");
            String stmt = "SELECT  " +
            "user_id, action, ref_code_addr1, ref_code_addr2, ref_code_addr3, ref_code_addr4, ref_code_addr5, ref_code_addr6, ref_code_addr7, " +
            "acc_profile, card_name, carrier_code, city, country, card_profile, card_user_data, cur_code, " +
            "design_ref, date_of_birth, first_name, last_name, inst_code, language, new_acc, new_cust, post_code, " +
            "program_id, rec_id, status_code, cri_file_id, card_product, you_id, card_id" +
            "FROM ucardcrifis_card_creation WHERE user_id = '" + userId + "' ORDER BY updated_at desc LIMIT 1;";
            ResultSet rs = conn_.executeQuery(stmt);
            if (rs != null && rs.next()) {
                resultMap.put("userId",rs.getString(1));
                resultMap.put("action",rs.getString(2));
                resultMap.put("ref_code_addr1",rs.getString(3));
                resultMap.put("ref_code_addr2",rs.getString(4));
                resultMap.put("ref_code_addr3",rs.getString(5));
                resultMap.put("ref_code_addr4",rs.getString(6));
                resultMap.put("ref_code_addr5",rs.getString(7));
                resultMap.put("ref_code_addr6",rs.getString(8));
                resultMap.put("ref_code_addr7",rs.getString(9));
                resultMap.put("accProfile",rs.getString(10));
                resultMap.put("cardName",rs.getString(11));
                resultMap.put("carrierCode",rs.getString(12));
                resultMap.put("city",rs.getString(13));
                resultMap.put("country",rs.getString(14));
                resultMap.put("cardProfile",rs.getString(15));
                resultMap.put("cardUserData",rs.getString(16));
                resultMap.put("curCode",rs.getString(17));
                resultMap.put("designRef",rs.getString(18));
                resultMap.put("dateOfBirth",rs.getString(19));
                resultMap.put("firstName",rs.getString(20));
                resultMap.put("lastName",rs.getString(21));
                resultMap.put("instCode",rs.getString(22));
                resultMap.put("language",rs.getString(23));
                resultMap.put("newAcc",rs.getString(24));
                resultMap.put("newCust",rs.getString(25));
                resultMap.put("postCode",rs.getString(26));
                resultMap.put("programId",rs.getString(27));
                resultMap.put("recId",rs.getString(28));
                resultMap.put("statusCode",rs.getString(29));
                resultMap.put("criFileId",rs.getString(30));
                resultMap.put("cardProduct",rs.getString(31));
                resultMap.put("youId",rs.getString(32));
                resultMap.put("cardIdToken",rs.getString(33));
                return resultMap;
            }else{
                System.out.println("CriFile informaiton is disable to retrieve");
                return null;
            }
        }catch(SQLException sqlex){
            System.out.println("CriFile informaiton is disable to retrieve");
            return null;
        }
    }
}
