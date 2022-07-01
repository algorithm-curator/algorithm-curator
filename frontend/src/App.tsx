import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";

import Header from "Components/Header";
import Main from "Pages/Main";
import "./Styles/style.css";

function App() {
	return (
		<BrowserRouter>
			<Header />
			<Routes>
				<Route path="/" element={<Main />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
