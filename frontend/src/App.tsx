import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Header from "Components/Header";
import Main from "Pages/Main";
import ProblemToday from "Pages/ProblemToday";
import ProblemList from "Pages/ProblemList";
import Rank from "Pages/Rank";
import MyChart from "Pages/MyChart";
import "./Styles/style.css";

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
			</Routes>
		</BrowserRouter>
	);
}

export default App;
