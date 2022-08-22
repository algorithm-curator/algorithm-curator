import styled from "@emotion/styled";
import { Link } from "react-router-dom";

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-items: center;
	margin: 7rem auto;
	background-color: #e0e0e0;
	width: 90%;
	max-width: 1600px;
	border-radius: 1rem;
	padding: 2rem;
`;

export const TitleChartWrapper = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: flex-end;
`;

export const Title = styled.div`
	font-size: 2rem;
	font-weight: 600;
	padding: 1rem;
	text-align: left;
`;

export const ChartLink = styled(Link)`
	text-decoration: none;
	color: black;
	font-weight: 500;
	font-size: 1.3rem;
`;

export const MoreButton = styled.button`
	width: 10%;
	font-size: 1.2rem;
	padding: 0.5rem;
	background-color: #4d4acf;
	color: white;
	cursor: pointer;
	border: none;
	border-radius: 1rem;
	margin-bottom: 1rem;
`;

export const ToastMessage = styled.div`
	color: red;
	font-size: 0.8rem;
`;
