import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Header from "Components/Header";
import Main from "Pages/Main";
import ProblemToday from "Pages/ProblemToday";
import ProblemList from "Pages/ProblemList";
import "./Styles/style.css";

function App() {
	return (
		<BrowserRouter>
			<Header />
			<Routes>
				<Route path="/" element={<Main />} />
				<Route path="/problem/" element={<ProblemToday />} />
				<Route path="/problemlist" element={<ProblemList />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
