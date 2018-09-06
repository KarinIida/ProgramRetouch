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
import beans.UserDataBeans;
import dao.BuyDAO;
import dao.UserDAO;

/**
 * ユーザー情報画面
 *
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserData")
public class UserData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// セッション開始
		HttpSession session = request.getSession();
		try {
			// ログイン時に取得したユーザーIDをセッションから取得
			int userId = (int) session.getAttribute("userId");
			// 更新確認画面から戻ってきた場合Sessionから取得。それ以外はuserIdでユーザーを取得
			UserDataBeans udb = session.getAttribute("returnUDB") == null ? UserDAO.getUserDataBeansByUserId(userId) : (UserDataBeans) EcHelper.cutSessionAttribute(session, "returnUDB");


			// セッションからカート情報を取得
			ArrayList<ItemDataBeans> cart = (ArrayList<ItemDataBeans>) EcHelper.cutSessionAttribute(session, "cart");

			BuyDataBeans bdb = (BuyDataBeans) EcHelper.cutSessionAttribute(session, "bdb");

//			登録はしてあるけど、buyIdをどうにかしないといけない
//			// 購入情報を登録
//			int buyId = BuyDAO.insertBuy(bdb);
//			// 購入詳細情報を購入情報IDに紐づけして登録
//			for (ItemDataBeans cartInItem : cart) {
//				BuyDetailDataBeans bddb = new BuyDetailDataBeans();
//				bddb.setBuyId(buyId);
//				bddb.setItemId(cartInItem.getId());
//				BuyDetailDAO.insertBuyDetail(bddb);
//			}

			/* ====購入完了ページ表示用==== */
			BuyDataBeans resultBDB = BuyDAO.getBuyDataBeansByBuyId(userId);
			request.setAttribute("resultBDB", resultBDB);

			ArrayList<BuyDataBeans> a = (ArrayList<BuyDataBeans>) EcHelper.cutSessionAttribute(session, "a");

//			買い物カゴ
//			ArrayList<ItemDataBeans> cart = (ArrayList<ItemDataBeans>) session.getAttribute("cart");
//			ビーンズ（購入日時・配送方法・購入金額）
//			BuyDataBeans
//			配送方法
//			int inputDeliveryMethodId = Integer.parseInt(request.getParameter("delivery_method_id"));
//			DeliveryMethodDataBeans userSelectDMB = DeliveryMethodDAO.getDeliveryMethodDataBeansByID(inputDeliveryMethodId);
//			インスタンス？なんの？
//			BuyDataBeans bdb = new BuyDataBeans();
//			bdb.setUserId((int) session.getAttribute("userId"));
//			bdb.setTotalPrice(totalPrice);
//			bdb.setDelivertMethodId(userSelectDMB.getId());
//			bdb.setDeliveryMethodName(userSelectDMB.getName());

//			BuyDataBeans bdb = (BuyDataBeans) EcHelper.cutSessionAttribute(session, "bdb");
//			int buyId = BuyDAO.insertBuy(bdb);
//			BuyDataBeans a = BuyDAO.getBuyDataBeansByBuyId(buyId);
//			request.setAttribute("a", a);


			// 入力された内容に誤りがあったとき等に表示するエラーメッセージを格納する
			String validationMessage = (String) EcHelper.cutSessionAttribute(session, "validationMessage");


			request.setAttribute("validationMessage", validationMessage);
			request.setAttribute("udb", udb);

			request.getRequestDispatcher(EcHelper.USER_DATA_PAGE).forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}
	}
}
