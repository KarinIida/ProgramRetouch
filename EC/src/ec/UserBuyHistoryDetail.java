package ec;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;
import dao.BuyDetailDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
		try {

			int userId = (int) session.getAttribute("userId");

//		    int buyId = (int) session.getAttribute("buyId");
		    int buyId = Integer.parseInt(request.getParameter("buy_id"));
//			BuyDataBeans bdb = (BuyDataBeans) EcHelper.cutSessionAttribute(session, "bdb");
//		    int buyId = BuyDAO.insertBuy(bdb);
//		    int buyId = BuyDAO.getBuyDataBeansByBuyId(buyId);

			BuyDataBeans resultBDB = BuyDAO.getBuyDataBeansByBuyId(buyId);
			request.setAttribute("resultBDB", resultBDB);
//sql dao buy_id
			ArrayList<ItemDataBeans> buyIDD = BuyDetailDAO.BuyItemDateDetail(buyId);
			request.setAttribute("buyIDD", buyIDD);

			ItemDataBeans b = BuyDAO.getItemDataBeansByBuyId(buyId);
			request.setAttribute("b", b);

//			DeliveryMethodDataBeans userSelectDMB = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(buyId);
//			BuyDataBeans bdb = new BuyDataBeans();
//			bdb.setUserId((int) session.getAttribute("userId"));
//			bdb.setDelivertMethodId(userSelectDMB.getId());
//			bdb.setDeliveryMethodName(userSelectDMB.getName());
//			bdb.setDeliveryMethodPrice(userSelectDMB.getPrice());

//el式 buyIDBList
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

	}
}
