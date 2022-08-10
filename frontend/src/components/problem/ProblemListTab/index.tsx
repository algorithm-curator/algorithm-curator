/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-explicit-any */
import { deleteProblems, putProblemStatus } from "apis/problem";
import React, { useState } from "react";
import { API_TOKEN } from "Utils/localStorageKeys";
import {
	Container,
	Title,
	SolveLevelWrapper,
	Level,
	Origin,
	ChangeStatus,
	StatusSelect,
	CompleteButton,
} from "./styles";

function ProblemListTab({ problemInfo, getProblems, filterStatus }: any) {
	const [showSelect, setShowSelect] = useState(false);
	const [problemStatus, setProblemStatus] = useState<number | null>(null);
	const apiToken = localStorage.getItem(API_TOKEN);
	const onClickShow = () => {
		setShowSelect(!showSelect);
	};
	const onChangeStatus = (e: any) => {
		setProblemStatus(e.target.value);
	};
	const onClickComplete = () => {
		const statusTemp = Number(problemStatus);
		if (statusTemp) {
			if (statusTemp === 3) {
				(async () => {
					await deleteProblems(apiToken, problemInfo.id);
					getProblems(false, filterStatus, false);
				})();
			} else {
				(async () => {
					await putProblemStatus(apiToken, problemInfo.id, statusTemp)
						.then((res) => {
							getProblems(false, filterStatus, false);
						})
						.catch((err) => {
							console.log(err);
						});
				})();
			}
		}
		onClickShow();
	};
	return (
		<Container>
			<Title href={problemInfo.quiz_url} target="_blank">
				[{problemInfo.quiz_platform}] {problemInfo.title}
			</Title>
			<SolveLevelWrapper>
				{showSelect ? (
					<div style={{ display: "flex", alignItems: "center" }}>
						<StatusSelect onChange={onChangeStatus}>
							<option value={1}>풀이 미완료</option>
							<option value={2}>풀이 완료</option>
							<option value={3}>삭제</option>
						</StatusSelect>
						<CompleteButton onClick={onClickComplete}>변경완료</CompleteButton>
					</div>
				) : (
					<ChangeStatus onClick={onClickShow}>문제상태변경</ChangeStatus>
				)}
				<Level>{problemInfo.quiz_level}</Level>
				<Origin>{problemInfo.quiz_platform}</Origin>
			</SolveLevelWrapper>
		</Container>
	);
}

export default ProblemListTab;
