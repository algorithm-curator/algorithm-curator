import React, { useState, useEffect, useRef } from "react";
import { checkNickname, putMyProfile } from "apis/user";
import { API_TOKEN } from "utils/localStorageKeys";
import {
	Container,
	NicknameCheck,
	ProfileEditSubmit,
	ProfileImage,
	ProfileWrapper,
	Title,
} from "./styles";

function MyPage() {
	const imageInput = useRef<HTMLInputElement>(null);
	const [nicknameText, setNicknameText] = useState<string>("");
	const [nicknameCheckState, setNicknameCheckState] = useState<boolean>(false);
	const apiToken = localStorage.getItem(API_TOKEN);
	const onClickImage = () => {
		if (imageInput.current) imageInput.current.click();
	};
	const onClickNicknameCheck = () => {
		const getCheckNickname = async () => {
			await checkNickname(apiToken, nicknameText)
				.then((res) => {
					if (res.data.response.result) {
						alert("사용가능한 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					} else {
						alert("중복된 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					}
				})
				.catch((err) => console.log(err));
		};

		getCheckNickname();
	};
	const onClickEditSubmit = () => {
		let status = "";
		if (!nicknameCheckState) {
			status = "닉네임 중복체크를 해주세요.";
		} else if (nicknameText.length < 3) {
			status = "닉네임이 짧습니다.";
		} else if (nicknameText.search(/\s/) !== -1) {
			status = "닉네임은 빈 칸을 포함 할 수 없습니다.";
		}
		if (status === "") {
			const tryPutMyProfile = async () => {
				await putMyProfile(apiToken, nicknameText)
					.then((res) => {
						console.log(res);
					})
					.catch((err) => {
						console.log(err);
					});
			};
			tryPutMyProfile();
		} else {
			alert(status);
		}
	};

	return (
		<Container>
			<Title>내 프로필</Title>
			<ProfileWrapper>
				<ProfileImage
					srcUrl={`${`${`${process.env.PUBLIC_URL}DefaultProfileImage.png`}`}`}
					onClick={onClickImage}
				>
					<input type="file" style={{ display: "none " }} ref={imageInput} />
				</ProfileImage>
				<div
					style={{
						display: "flex",
						justifyContent: "center",
						alignItems: "center",
						marginTop: "2rem",
					}}
				>
					<input
						style={{ border: "none", fontSize: "1.7rem", marginTop: "auto" }}
						type="text"
						placeholder="내닉네임"
						onChange={(event) => setNicknameText(event.target.value)}
					/>
					<NicknameCheck onClick={onClickNicknameCheck}>중복체크</NicknameCheck>
				</div>
				<ProfileEditSubmit onClick={onClickEditSubmit}>
					수정 완료
				</ProfileEditSubmit>
			</ProfileWrapper>
		</Container>
	);
}

export default MyPage;
