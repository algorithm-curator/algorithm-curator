/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { getSolvedRate, getSolvedTrace } from "apis/statistics";
import React, { useEffect, useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import moment from "moment";
import { API_TOKEN } from "Utils/localStorageKeys";
import { Container, Text, Time } from "./styles";

function UntilNowSolveCount() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [value, onChange] = useState(new Date());
	const [solvedCount, setSolvedCount] = useState<number>(0);
	const [marks, setMarks] = useState<string[]>([]);

	useEffect(() => {
		(async () => {
			await getSolvedRate(apiToken)
				.then((res) => {
					setSolvedCount(res.data.response.solved_count);
					console.log(res.data.response);
				})
				.catch((err) => {
					console.log(err);
				});
		})();
		(async () => {
			await getSolvedTrace(apiToken)
				.then((res) => {
					const tempDates: any[] = [];
					res.data.response.solved_states.forEach((info: any) => {
						tempDates.push(info.date);
					});
					setMarks([...tempDates]);
				})
				.catch((err) => {
					alert("트레이스를 가져오는데 문제가 생겼습니다.");
				});
		})();
	}, []);

	return (
		<Container>
			<Text style={{ marginBottom: "2rem" }}>지금까지 이만큼 풀었어요🎉</Text>
			<Text style={{ marginBottom: "1rem" }}>{solvedCount}</Text>
			<Time>{moment().format("YYYY.MM.DD HH:mm")}</Time>
			<Calendar
				onChange={onChange}
				value={value}
				minDetail="month"
				maxDetail="month"
				prev2Label={null}
				next2Label={null}
				tileClassName={({ date, view }) => {
					if (marks.find((x) => x === moment(date).format("YYYY-MM-DD"))) {
						return "highlight";
					}
					return null;
				}}
				onActiveStartDateChange={({ activeStartDate }) => {
					const date = moment(activeStartDate).format("YYYY-MM-DD");
					(async () => {
						await getSolvedTrace(apiToken, date)
							.then((res) => {
								const tempDates: any[] = [];
								res.data.response.solved_states.forEach((info: any) => {
									tempDates.push(info.date);
								});
								setMarks([...tempDates]);
							})
							.catch((err) => {
								alert("트레이스를 가져오는데 문제가 생겼습니다.");
							});
					})();
				}}
			/>
		</Container>
	);
}

export default UntilNowSolveCount;
