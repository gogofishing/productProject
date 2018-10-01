package com.product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.util.DividePage;
import com.util.UUIDTools;


/**
 * Servlet implementation class ProductAction
 */
@WebServlet("/ProductAction")
public class ProductAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ProductDao service = new ProductDao();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String action_flag = request.getParameter("action_flag");
		if (action_flag.equals("add")) {
			addProduct(request, response);
		}
		else if (action_flag.equals("search")) {
			listProduct(request, response);
		}
		else if (action_flag.equals("del")) {
			delProduct(request, response);
		}
		else if (action_flag.equals("view")) {
			viewProduct(request, response);
		}
		out.flush();
		out.close();
	}

	private void viewProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String proid = request.getParameter("proid");
		Map<String, Object> map = service.viewProduct(proid);
		request.setAttribute("productMap", map);
		try {
			request.getRequestDispatcher("/viewProduct.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void delProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		System.out.println("into del");
		String[] ids = request.getParameterValues("ids");
		for (int i = 0; i < ids.length; i++) {
			System.out.println("ids[" + i + "]=" + ids[i]);
		}
		boolean flag = service.delProduct(ids);
		System.out.println("delete flag:" + flag);
		if (flag) {
			try {
				request.getRequestDispatcher("/main.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void listProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String productName = request.getParameter("proname");
		String pageNum = request.getParameter("pageNum");
		if (productName == null) {
			productName = "";
		}
		int totalRecord = service.getItemCount(productName);
		int currentPage = 1;
		DividePage dividePage = new DividePage(5, totalRecord);
		if (pageNum != null) {
			currentPage = Integer.parseInt(pageNum);
			dividePage.setCurrentPage(currentPage);
		}
		int start = dividePage.fromIndex();
		int end = dividePage.toIndex();
		System.out.println("currentPageNum :"+ dividePage.getCurrentPage() +", start = "+start +", end = "+end);
		List<Map<String, Object>> list =null;
		try {
			list = service.listProduct(productName, start, end);
			request.setAttribute("listProduct", list);
			request.setAttribute("dividePage", dividePage);
			request.setAttribute("productName", productName);
			request.getRequestDispatcher("/main.jsp").forward(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addProduct(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		String path = request.getContextPath();
		DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
		ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
		servletFileUpload.setFileSizeMax(3 * 1024 * 1024); //sigle file size limit 3M
		servletFileUpload.setSizeMax(6 * 1024 * 1024);	// file upload limit 6M
		List<FileItem> list = null;
		List<Object> params = new ArrayList<Object>();
		params.add(UUIDTools.getUUID());
		
		try {
			list = servletFileUpload.parseRequest(request);
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					String fileItemName = fileItem.getFieldName();
					String fileItemValue = fileItem.getString("utf-8");
					if (fileItemName.equals("proname")) {
						params.add(fileItemValue);
					}
					else if (fileItemName.equals("proprice")) {
						params.add(fileItemValue);
					}
					else if (fileItemName.equals("proaddress")) {
						params.add(fileItemValue);
					}
				}
				else {
					String imageName = fileItem.getName();
					params.add(imageName);
//					String upload_dir = request.getServletContext().getRealPath("/upload");
					String image_dir = "E:\\Java_workspace\\productProject\\WebContent\\upload"; 	//change the upload path to solve the missing image in tomcat, but chrome doesnot allow to read the local file
//					File uploadFile = new File(upload_dir + "/" + imageName);
					File uploadFile = new File(image_dir + "/" + imageName);
//					System.out.println("---upload_dir--->>" + upload_dir);
					System.out.println("---upload_dir--->>" + image_dir);
					fileItem.write(uploadFile);
				}
				
			}
			boolean flag = service.addProduct(params);
			if (flag) {
				response.sendRedirect(path + "/main.jsp"); 
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
