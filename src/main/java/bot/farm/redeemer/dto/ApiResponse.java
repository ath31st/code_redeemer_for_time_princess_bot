package bot.farm.redeemer.dto;

/**
 * Represents an API response with a code and a message.
 * This record is a data structure used for holding information about the response
 * from an API, including an integer code and a String message.
 *
 * @param code The integer code indicating the status of the API response.
 * @param msg  The String message providing additional information about the response.
 */
public record ApiResponse(
    int code,
    String msg
) {
}
