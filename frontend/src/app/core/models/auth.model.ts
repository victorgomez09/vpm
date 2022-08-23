export interface ILogin {
  email: string;
  password: string;
};

export interface IRegister {
  email: string;
  password: string;
  fullname: string;
  image: string;
};

export interface IUser {
  id: string;
  email: string;
  fullname: string;
  image: string;
};
