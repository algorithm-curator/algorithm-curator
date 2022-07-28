import React, { useEffect, useState } from "react";
import { getTodayProblems } from "apis/problem";
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
	const DrawProblems = async () => {
		await getTodayProblems(apiToken)
			.then((res) => {
				setTodayProblems(res.data.response);
			})
			.catch((err) => {
				alert("문제를 가져오는데 에러가 발생했습니다.");
				console.log(err);
			});
	};

	return (
		<Container>
			<ProblemDraw drawProblems={DrawProblems} />
			{todayProblems
				? todayProblems?.map((problem) => {
						return <ProblemTab problemInfo={problem} />;
				  })
				: null}
			<ProblemListTextWrapper>
				<ProblemStatusButton>문제상태 수정완료</ProblemStatusButton>
				<ProblemListText to="/problemlist">
					모든 문제목록 보러가기
				</ProblemListText>
			</ProblemListTextWrapper>
		</Container>
	);
}

export default ProblemToday;
