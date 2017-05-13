package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import team.even.jobcrawler.model.db.dao.IDistrictDAO;
import team.even.jobcrawler.model.db.vo.District;
import team.even.jobcrawler.model.db.vo.JobKind;

public class DistrictDAOimpl implements IDistrictDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public DistrictDAOimpl(Connection conn)
	{
		this.conn = conn;
	}
	
	@Override
	public boolean doCreate(District district)
	{
		boolean flag = false;
		String sql = "insert into district values (?)";
		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, district.getDistrict());
			if(pstmt.executeUpdate() > 0)
			{
				flag = true;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(conn != null)
					{
						try
						{
							conn.close();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
		return flag;
	}

	@Override
	public List<District> findAll()
	{
		List<District> dataList = new ArrayList<District>();
		String sql = "select * from district";
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				District data = new District();
				data.setDistrict(rs.getString("district"));
				dataList.add(data);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(rs != null)
			{
				try
				{
					rs.close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(pstmt != null)
					{
						try
						{
							pstmt.close();
						} catch (SQLException e)
						{
							e.printStackTrace();
						}
						finally
						{
							if(conn != null)
							{
								try
								{
									conn.close();
								} catch (SQLException e)
								{
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
		
		return dataList;
	}
	
}
