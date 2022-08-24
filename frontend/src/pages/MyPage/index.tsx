/* eslint-disable @typescript-eslint/no-non-null-assertion */
/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */
import React, { useState, useEffect, useRef } from "react";
import { useRecoilState } from "recoil";
import { isLoggedState } from "stores/Auth";
import { checkNickname, getMyProfile, putMyProfile } from "apis/user";
import { API_TOKEN } from "Utils/localStorageKeys";
import axios from "axios";
import {
	Container,
	NicknameCheck,
	ProfileEditSubmit,
	ProfileImage,
	ProfileWrapper,
	Title,
	LimitTextBox,
} from "./styles";

// 이미지 URL, 이미지 파일 -> 두 가지가 있는데 API 사용할때 유의.
// * process.env.PUBLIC_URL -> 빈 문자열 출력되는 현상
function MyPage() {
	const imageInput = useRef<HTMLInputElement>(null);
	const [nicknameText, setNicknameText] = useState<string>("");
	const [initialNickname, setInitialNickname] = useState<string>("");
	const [nicknameCheckState, setNicknameCheckState] = useState<boolean>(false);
	const [limitNicknameText, setLimitNicknameText] = useState<string>("");
	const [imageObject, setImageObject] = useState<any>({
		file: "",
		fileName: "",
	});
	const [previewImage, setPreviewImage] = useState<any>("");
	const [isLogged, setIsLogged] = useRecoilState(isLoggedState);
	const apiToken = localStorage.getItem(API_TOKEN);
	const onClickImage = () => {
		if (imageInput.current) imageInput.current.click();
	};
	const onClickNicknameCheck = () => {
		const getCheckNickname = async () => {
			await checkNickname(apiToken, nicknameText)
				.then((res) => {
					// API 변경 요청 -> response에 닉네임 담아서, 이유는 기존 닉네임 중복 체크 시 로직 까다롭지않게
					if (res.data.response.result) {
						alert("사용가능한 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					} else {
						alert("중복된 닉네임입니다.");
						setNicknameCheckState(res.data.response.result);
					}
				})
				.catch((err) => {
					if (err.response.status === 401) {
						setIsLogged(false);
						alert("로그인 토큰이 만료되었습니다. 다시 로그인 해주세요.");
					} else {
						console.log(err);
					}
				});
		};
		if (nicknameText && nicknameText === initialNickname) {
			alert("같은 닉네임입니다.");
			setNicknameCheckState(true);
		} else if (nicknameText) getCheckNickname();
		else alert("닉네임을 입력하세요.");
	};
	const limitNickname = (name: string) => {
		let status = "";
		if (!name) {
			status = "닉네임을 입력해주세요";
			return status;
		}
		if (name.length < 2 || name.length > 17) {
			status = "닉네임은 2자 이상 16자 이하로 가능합니다.";
		} else if (name.search(/\s/) !== -1) {
			status = "닉네임에 빈 칸을 포함 할 수 없습니다.";
		}
		// 사용가능하다면 false 리턴
		return status;
	};
	const onChangeImage = (e: any) => {
		const reader = new FileReader();
		const file = e.target.files![0];
		reader.onloadend = () => {
			setImageObject({
				file,
				fileName: file.name,
			});
			setPreviewImage(reader.result);
		};
		reader.readAsDataURL(file);
	};
	const onClickEditSubmit = () => {
		if (nicknameCheckState && !limitNickname(nicknameText)) {
			const tryPutMyProfile = async () => {
				await putMyProfile(
					apiToken,
					nicknameText,
					imageObject?.fileName?.includes("amazonaws.com")
						? `${imageObject.fileName}.png`
						: imageObject.fileName
				)
					.then((res) => {
						// 헤더 상태변경하면됨
						if (imageObject.fileName && imageObject.file) {
							axios
								.put(res.data.response.profile_image, imageObject.file)
								.then((res) => {
									if (res.status === 200) setIsLogged(true);
								})
								.catch((err) => console.log(err));
						}
						setInitialNickname(nicknameText);
						alert("프로필이 수정되었습니다.");
					})
					.catch((err) => {
						alert("수정에 실패했습니다.");
					});
			};
			tryPutMyProfile();
		} else {
			alert("닉네임을 확인해주세요.");
		}
	};

	useEffect(() => {
		(async () => {
			await getMyProfile(apiToken)
				.then((res) => {
					const nicknameTemp = res?.data?.response.nickname;
					const UrlTemp = res?.data?.response?.profile_image;
					// 초기 닉네임, 닉네임 input 셋팅
					setInitialNickname(nicknameTemp);
					setNicknameText(nicknameTemp);
					setLimitNicknameText(limitNickname(nicknameTemp));
					// 이미지 URL 셋팅
					setPreviewImage(UrlTemp);
					setImageObject({ ...imageObject, fileName: UrlTemp });
				})
				.catch((err) => {
					if (err.status === 401) {
						setIsLogged(false);
						alert("로그인 토큰이 만료되었습니다. 다시 로그인 해주세요.");
					}
				});
		})();
	}, []);

	return (
		<Container>
			<Title>내 프로필</Title>
			<ProfileWrapper>
				<ProfileImage
					srcUrl={previewImage || "/DefaultProfileImage.png"}
					onClick={onClickImage}
				>
					<input
						type="file"
						accept="image/jpg, image/jpeg, image/png"
						style={{ display: "none " }}
						ref={imageInput}
						onChange={(e) => onChangeImage(e)}
					/>
				</ProfileImage>
				<div
					style={{
						marginTop: "1rem",
					}}
				>
					현재 닉네임 : {initialNickname}
				</div>
				<div>
					<div
						style={{
							display: "flex",
							justifyContent: "center",
							alignItems: "center",
							marginTop: "2rem",
						}}
					>
						<input
							style={{
								border: "none",
								fontSize: "1.3rem",
								marginTop: "auto",
								padding: "0.5rem 1rem",
								borderColor: limitNicknameText ? "red" : "green",
								borderStyle: "solid",
								borderWidth: "1px",
							}}
							type="text"
							placeholder="닉네임을 입력하세요."
							value={nicknameText}
							onChange={(event) => {
								setNicknameText(event.target.value);
								setLimitNicknameText(limitNickname(event.target.value));
							}}
							maxLength={20}
						/>
						<NicknameCheck onClick={onClickNicknameCheck}>
							중복체크
						</NicknameCheck>
					</div>
					<LimitTextBox>{limitNicknameText}</LimitTextBox>
				</div>
				<ProfileEditSubmit onClick={onClickEditSubmit}>
					수정 완료
				</ProfileEditSubmit>
			</ProfileWrapper>
		</Container>
	);
}

export default MyPage;
