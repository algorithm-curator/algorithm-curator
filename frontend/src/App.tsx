import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./Styles/style.css";

import Header from "Components/Header";
import Main from "Pages/Main";
import ProblemToday from "Pages/ProblemToday";
import ProblemList from "Pages/ProblemList";
import Rank from "Pages/Rank";
import MyChart from "Pages/MyChart";
import MyPage from "Pages/MyPage";

function App() {
	return (
		<BrowserRouter>
			<Header />
			<Routes>
				<Route path="/" element={<Main />} />
				<Route path="/problem/" element={<ProblemToday />} />
				<Route path="/problemlist" element={<ProblemList />} />
				<Route path="/mychart" element={<MyChart />} />
				<Route path="/rank" element={<Rank />} />
				<Route path="/mypage" element={<MyPage />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
