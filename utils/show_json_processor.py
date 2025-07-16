import json
import sys
from datetime import datetime


def is_ascii(s):
    return all(ord(c) < 128 for c in s)


def process_json(input_file_path, output_file_path):
    print(f"Processing file: {input_file_path}")
    filtered_shows = []

    try:
        with open(input_file_path, 'r', encoding='utf-8') as file:
            for line in file:
                try:
                    show = json.loads(line)

                    if 'original_name' in show and is_ascii(show['original_name']) and 'popularity' in show and show[
                        'popularity'] > 0.5:
                        filtered_shows.append(show)
                        print(f"Adding show: {show['original_name']}")
                    else:
                        print(f"Skipping show: {show['original_name']}")
                except json.JSONDecodeError as e:
                    print(f"Error decoding JSON: {e} in line: {line.strip()}")
    except FileNotFoundError:
        print(f"File not found: {input_file_path}")
        return

    print(f"Total shows retrieved: {len(filtered_shows)}")
    print("Sorting shows by popularity...")

    sorted_shows = sorted(filtered_shows, key=lambda x: x['popularity'], reverse=True)

    print(f"Writing output to {output_file_path}")
    with open(output_file_path, 'w', encoding='utf-8') as output_file:
        json.dump(sorted_shows, output_file, indent=2, ensure_ascii=False)

    print(f"Output written to {output_file_path}")


if __name__ == "__main__":
    input_file_path = sys.argv[1]
    input_file_name = input_file_path.split("/")[-1]

    output_file_path = datetime.now().strftime("%Y-%m-%d_%H-%M-%S") + "_" + input_file_name

    process_json(input_file_path, output_file_path)
