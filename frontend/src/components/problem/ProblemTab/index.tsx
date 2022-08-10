/* eslint-disable @typescript-eslint/no-explicit-any */
import React from "react";
import {
	Container,
	Title,
	SolveLevelWrapper,
	SolveStatus,
	Level,
} from "./styles";

function index({ problemInfo, setStatus, index }: any) {
	const onChangeSolveStatus = (e: any) => {
		setStatus(index, e.target.value);
	};
	console.log(problemInfo);
	return (
		<Container>
			<Title href={problemInfo.quiz_url} target="_blank">
				[{problemInfo.quiz_platform}] {problemInfo.title}
			</Title>
			<SolveLevelWrapper>
				<SolveStatus onChange={onChangeSolveStatus}>
					<option value={1}>풀이 미완료</option>
					<option value={2}>풀이 완료</option>
				</SolveStatus>
				<Level>{problemInfo.quiz_level}</Level>
			</SolveLevelWrapper>
		</Container>
	);
}

export default index;
