import { getSolvedRate } from "apis/statistics";
import React, { useEffect, useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { API_TOKEN } from "Utils/localStorageKeys";
import { Container, Text, Time } from "./styles";

function UntilNowSolveCount() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [value, onChange] = useState(new Date());
	const [solvedCount, setSolvedCount] = useState<number>(0);

	useEffect(() => {
		(async () => {
			await getSolvedRate(apiToken)
				.then((res) => {
					setSolvedCount(res.data.response.solved_count);
				})
				.catch((err) => {
					console.log(err);
				});
		})();
	}, []);

	return (
		<Container>
			<Text style={{ marginBottom: "2rem" }}>지금까지 이만큼 풀었어요🎉</Text>
			<Text style={{ marginBottom: "1rem" }}>{solvedCount}</Text>
			<Time>2022.07.11 00:00</Time>
			<Calendar
				onChange={onChange}
				value={value}
				minDetail="month"
				maxDetail="month"
			/>
		</Container>
	);
}

export default UntilNowSolveCount;
