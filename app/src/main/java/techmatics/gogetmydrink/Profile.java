package techmatics.gogetmydrink;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile {
    String  FIRST_NAME;
    String  LAST_NAME;
    String  EMAILID;
    String MOBILE;
    String  CATEGORY;
    String  RANK;
    String  IMAGE;
    ArrayList<DeliveryLocation> deliveryList=new ArrayList<>();
    public Profile(JSONObject jsonObject)
    {try {
        FIRST_NAME=jsonObject.isNull("FIRST_NAME")?"0.0":jsonObject.getString("FIRST_NAME");
        LAST_NAME=jsonObject.isNull("LAST_NAME")?"0.0":jsonObject.getString("LAST_NAME");
        EMAILID=jsonObject.isNull("EMAILID")?"0.0":jsonObject.getString("EMAILID");
        MOBILE=jsonObject.isNull("MOBILE")?"0.0":jsonObject.getString("MOBILE");
        CATEGORY=jsonObject.isNull("CATEGORY")?"0.0":jsonObject.getString("CATEGORY");
        RANK=jsonObject.isNull("RANK")?"0.0":jsonObject.getString("RANK");
        IMAGE=jsonObject.isNull("IMAGE")?"":jsonObject.getString("IMAGE");
        JSONArray jsonArray=jsonObject.getJSONArray("DELIVERY_AREA");
        for(int i=0;i<jsonArray.length();i++)
        {
            deliveryList.add(new DeliveryLocation(jsonArray.getJSONObject(i)));
        }
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    }


    public ArrayList<DeliveryLocation> getDeliveryList() {
        return deliveryList;
    }

    public String getRANK() {
        return RANK;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public String getEMAILID() {
        return EMAILID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public String getIMAGE() {
        if(IMAGE.length()>0)
        {
            IMAGE=IMAGE.replace("C:\\Publish\\","http://115.248.119.225/tsys/")  ;
        }
        return IMAGE;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public String getMOBILE() {
        return MOBILE;
    }
    public String getdeliveryArea()
    {
        String deliveryArea="";
        for(int i=0;i<deliveryList.size();i++)
        {
            if(i==0)
            {
                deliveryArea=deliveryList.get(i).getLOCATIONNAME();
            }  else {
                           deliveryArea+=",\n"+deliveryList.get(i).getLOCATIONNAME();
            }
        }
        return deliveryArea;
    }


    public class DeliveryLocation{
      String   LOCATIONNAME;
      String   LAT;
       String  LON;
       public DeliveryLocation(JSONObject jsonObject)
       {
           try{
               this.LAT=jsonObject.isNull("LAT")?"0.0":jsonObject.getString("LAT");
               this.LON=jsonObject.isNull("LON")?"0.0":jsonObject.getString("LON");
               this.LOCATIONNAME=jsonObject.isNull("LOCATIONNAME")?"0.0":jsonObject.getString("LOCATIONNAME");
           }catch (Exception ex)
           {
               ex.fillInStackTrace();
           }
       }

        public String getLAT() {
            return LAT;
        }

        public String getLOCATIONNAME() {
            return LOCATIONNAME;
        }

        public String getLON() {
            return LON;
        }
    }
}
