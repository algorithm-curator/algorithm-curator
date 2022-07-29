import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { Container, Text, Time } from "./styles";

function UntilNowSolveCount() {
	const [value, onChange] = useState(new Date());

	return (
		<Container>
			<Text style={{ marginBottom: "2rem" }}>지금까지 이만큼 풀었어요🎉</Text>
			<Text style={{ marginBottom: "1rem" }}>36</Text>
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
