export const getFirstWordOfString = (text: string) => {
    const textArray: string[] = text.split(" ");
    return textArray[0];
}