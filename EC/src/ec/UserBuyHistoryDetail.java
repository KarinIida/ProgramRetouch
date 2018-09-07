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
import dao.BuyDetailDAO;
import dao.UserDAO;

/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// セッション開始
		HttpSession session = request.getSession();
		try {
			// ログイン時に取得したユーザーIDをセッションから取得
			int userId = (int) session.getAttribute("userId");
			// 更新確認画面から戻ってきた場合Sessionから取得。それ以外はuserIdでユーザーを取得
			UserDataBeans udb = session.getAttribute("returnUDB") == null ? UserDAO.getUserDataBeansByUserId(userId) : (UserDataBeans) EcHelper.cutSessionAttribute(session, "returnUDB");

//買ったidをすべて表示したい
			/* ====購入完了ページ表示用==== */
			BuyDataBeans resultBDB = BuyDAO.getBuyDataBeansByBuyId(userId);
			request.setAttribute("resultBDB", resultBDB);
			// 購入アイテム情報
			ArrayList<ItemDataBeans> buyIDBList = BuyDetailDAO.getItemDataBeansListByBuyId(userId);
			request.setAttribute("buyIDBList", buyIDBList);


		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);

	}
}
