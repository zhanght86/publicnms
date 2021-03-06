package com.afunms.topology.dao;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.afunms.common.base.BaseDao;
import com.afunms.common.base.BaseVo;
import com.afunms.common.base.DaoInterface;
import com.afunms.polling.om.CDP;
import com.afunms.polling.om.NDP;

public class CDPDao extends BaseDao implements DaoInterface {
	
	public CDPDao() {
		super("nms_cdp");
		// TODO Auto-generated constructor stub
    }

	@Override
	public BaseVo loadFromRS(ResultSet rs) {
		// TODO Auto-generated method stub
		CDP cdp = new CDP();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			cdp.setId(rs.getLong("id"));
			cdp.setNodeid(rs.getLong("nodeid"));
			cdp.setDeviceIP(rs.getString("deviceip"));
			cdp.setPortName(rs.getString("portname"));		
			String collecttime = rs.getString("collecttime");
			Date date = sdf.parse(collecttime);
			Calendar calendar = Calendar.getInstance(); 
		    calendar.setTime(date); 
		    cdp.setCollecttime(calendar);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return cdp;
	}

	public boolean save(BaseVo vo) {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CDP ndpvo = (CDP) vo;
		StringBuffer addsql = new StringBuffer(200);
		addsql.append("insert into nms_cdp(nodeid,deviceid,portname,collecttime)values(");
		addsql.append(ndpvo.getNodeid());
		addsql.append(",'");
		addsql.append(ndpvo.getDeviceIP());
		addsql.append("','");
		addsql.append(ndpvo.getPortName());
		addsql.append("','");
		addsql.append(df.format(ndpvo.getCollecttime().getTime()));	
		addsql.append("')");
		return saveOrUpdate(addsql.toString());
	}

	public boolean update(BaseVo vo) {
		// TODO Auto-generated method stub
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		CDP ndpvo = (CDP) vo;
		StringBuffer updatesql = new StringBuffer(200);
		updatesql.append("update nms_cdp set nodeid=");
		updatesql.append(ndpvo.getNodeid());
		updatesql.append(",deviceid='");
		updatesql.append(ndpvo.getDeviceIP());
		updatesql.append("',portname='");
		updatesql.append(ndpvo.getPortName());
		updatesql.append("',collecttime='");
		updatesql.append(df.format(ndpvo.getCollecttime().getTime()));
		updatesql.append("' where id=");
		updatesql.append(ndpvo.getId());
		return saveOrUpdate(updatesql.toString());
	}
	
	public boolean delete(BaseVo vo)
	{
		CDP ndpvo = (CDP) vo;
		boolean b = false;
		String delsql = "delete from nms_cdp where id=" + ndpvo.getId();
		try {
			conn.executeUpdate(delsql);
			b = true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}
	
	public List queryall()
	{
		List listall = new ArrayList();
		String queryallsql = "select * from nms_cdp";
		try {
			rs = conn.executeQuery(queryallsql);
			while(rs.next())
			{
				listall.add(loadFromRS(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("list all cdp error");
			e.printStackTrace();
		}
		return listall;
	}
	
	public List getbynodeid(Long nodeid)
	{
		List list = new ArrayList();
		String queryonesql = "select * from nms_cdp where nodeid=" + nodeid;
		try {
			rs = conn.executeQuery(queryonesql);
			while(rs.next())
			{
				list.add(loadFromRS(rs));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("query cdp one error");
			e.printStackTrace();
		}
		return list;
	}
	
	public static void main(String[] args) {
		NDP ndp = new NDP();
		ndp.setNodeid(new Long(1));
		ndp.setDeviceId("ssss");
		ndp.setPortName("portname");
		ndp.setCollecttime(Calendar.getInstance());
		NDPDao nd = new NDPDao();
		nd.save(ndp);		
	}

}