import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { RecoilRoot } from "recoil";
import "./styles/style.css";

import Header from "components/Header";
import Main from "pages/Main";
import ProblemToday from "pages/ProblemToday";
import ProblemList from "pages/ProblemList";
import Rank from "pages/Rank";
import MyChart from "pages/MyChart";
import MyPage from "pages/MyPage";
import OAuth2RedirectHandler from "utils/KakaoLogin/OAuth2RedirectHandler";

function App() {
	return (
		<RecoilRoot>
			<BrowserRouter>
				<Header />
				<Routes>
					<Route path="/" element={<Main />} />
					<Route path="/problem/" element={<ProblemToday />} />
					<Route path="/problemlist" element={<ProblemList />} />
					<Route path="/mychart" element={<MyChart />} />
					<Route path="/rank" element={<Rank />} />
					<Route path="/mypage" element={<MyPage />} />
					{/* prettier-ignore */}
					<Route path='/oauth/callback/kakao' element={<OAuth2RedirectHandler />} />
				</Routes>
			</BrowserRouter>
		</RecoilRoot>
	);
}

export default App;
