export const getFirstWordOfString = (text: string) => {
  const textArray: string[] = text.split(' ');
  return textArray[0];
};

export const getInitials = (text: string) => {
  let names = text.split(' '),
    initials = names[0].substring(0, 1).toUpperCase();

  if (names.length > 1) {
    initials += names[names.length - 1].substring(0, 1).toUpperCase();
  }
  return initials;
};
