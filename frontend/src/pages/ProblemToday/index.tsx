import React, { useEffect, useState } from "react";
import { getTodayProblems, putProblemsStatus } from "apis/problem";
import ProblemTab from "components/problem/ProblemTab";
import ProblemDraw from "components/problem/ProblemDraw";
import { API_TOKEN } from "Utils/localStorageKeys";
import {
	Container,
	ProblemListTextWrapper,
	ProblemListText,
	ProblemStatusButton,
} from "./styles";

function ProblemToday() {
	const apiToken = localStorage.getItem(API_TOKEN);
	const [todayProblems, setTodayProblems] = useState<any[]>();
	const [solveStatus, setSolveStatus] = useState<any[]>([1, 1, 1]);
	const DrawProblems = async () => {
		await getTodayProblems(apiToken)
			.then((res) => {
				setTodayProblems(res.data.response);
				setSolveStatus([1, 1, 1]);
			})
			.catch((err) => {
				alert("문제를 가져오는데 에러가 발생했습니다.");
				console.log(err);
			});
	};
	const setStatus = (index: number, status: number) => {
		const tempStatus = solveStatus;
		tempStatus[index] = Number(status);
		setSolveStatus(tempStatus);
		console.log(tempStatus);
	};
	const onClickModifyRequest = () => {
		if (todayProblems) {
			const problems = [
				{ id: todayProblems[0].id, state: solveStatus[0] },
				{ id: todayProblems[1].id, state: solveStatus[1] },
				{ id: todayProblems[2].id, state: solveStatus[2] },
			];
			(async () => {
				await putProblemsStatus(apiToken, problems)
					.then((res) => {
						console.log(res);
						alert("수정되었습니다.");
					})
					.catch((err) => {
						console.log(err);
						alert("에러가 발생했습니다.");
					});
			})();
		} else {
			alert("문제를 뽑아주세요.");
		}
	};

	return (
		<Container>
			<ProblemDraw drawProblems={DrawProblems} />
			{todayProblems
				? todayProblems?.map((problem, i) => {
						return (
							<ProblemTab
								key={problem.id}
								problemInfo={problem}
								setStatus={setStatus}
								index={i}
							/>
						);
				  })
				: null}
			<ProblemListTextWrapper>
				<ProblemStatusButton onClick={onClickModifyRequest}>
					문제상태 수정완료
				</ProblemStatusButton>
				<ProblemListText to="/problemlist">
					모든 문제목록 보러가기
				</ProblemListText>
			</ProblemListTextWrapper>
		</Container>
	);
}

export default ProblemToday;
